package dev.stekl0.materialproductivity.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private object TestFirstTopLevelKey : NavKey

private object TestSecondTopLevelKey : NavKey

private object TestThirdTopLevelKey : NavKey

private object TestKeyFirst : NavKey

private object TestKeySecond : NavKey

class NavigatorTest {
    private lateinit var navigationState: NavigationState
    private lateinit var navigator: Navigator

    @BeforeEach
    fun setup() {
        val startKey = TestFirstTopLevelKey
        val topLevelStack = NavBackStack<NavKey>(startKey)
        val topLevelKeys =
            listOf(
                startKey,
                TestSecondTopLevelKey,
                TestThirdTopLevelKey,
            )
        val subStacks = topLevelKeys.associateWith { key -> NavBackStack(key) }

        navigationState =
            NavigationState(
                startKey = startKey,
                topLevelStack = topLevelStack,
                subStacks = subStacks,
            )
        navigator = Navigator(navigationState)
    }

    @Test
    fun testStartKey() {
        assertEquals(TestFirstTopLevelKey, navigationState.startKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun testNavigate() {
        navigator.navigate(TestKeyFirst)

        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
        assertEquals(TestKeyFirst, navigationState.subStacks[TestFirstTopLevelKey]?.last())
    }

    @Test
    fun testNavigateTopLevel() {
        navigator.navigate(TestSecondTopLevelKey)
        assertEquals(TestSecondTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun testNavigateSingleTop() {
        navigator.navigate(TestKeyFirst)

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )

        navigator.navigate(TestKeyFirst)

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )
    }

    @Test
    fun testNavigateTopLevelSingleTop() {
        navigator.navigate(TestSecondTopLevelKey)
        navigator.navigate(TestKeyFirst)

        assertIterableEquals(
            listOf(
                TestSecondTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )

        navigator.navigate(TestSecondTopLevelKey)

        assertIterableEquals(
            listOf(
                TestSecondTopLevelKey,
            ),
            navigationState.currentSubStack,
        )
    }

    @Test
    fun testSubStack() {
        navigator.navigate(TestKeyFirst)

        assertEquals(TestKeyFirst, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)

        navigator.navigate(TestKeySecond)

        assertEquals(TestKeySecond, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun testMultiStack() {
        // add to start stack
        navigator.navigate(TestKeyFirst)

        assertEquals(TestKeyFirst, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)

        // navigate to new top level
        navigator.navigate(TestSecondTopLevelKey)

        assertEquals(TestSecondTopLevelKey, navigationState.currentKey)
        assertEquals(TestSecondTopLevelKey, navigationState.currentTopLevelKey)

        // add to new stack
        navigator.navigate(TestKeySecond)

        assertEquals(TestKeySecond, navigationState.currentKey)
        assertEquals(TestSecondTopLevelKey, navigationState.currentTopLevelKey)

        // go back to start stack
        navigator.navigate(TestFirstTopLevelKey)

        assertEquals(TestKeyFirst, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun testPopOneNonTopLevel() {
        navigator.navigate(TestKeyFirst)
        navigator.navigate(TestKeySecond)

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
                TestKeySecond,
            ),
            navigationState.currentSubStack,
        )

        navigator.goBack()

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )

        assertEquals(TestKeyFirst, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun testPopOneTopLevel() {
        navigator.navigate(TestKeyFirst)
        navigator.navigate(TestSecondTopLevelKey)

        assertIterableEquals(
            listOf(
                TestSecondTopLevelKey,
            ),
            navigationState.currentSubStack,
        )

        assertEquals(TestSecondTopLevelKey, navigationState.currentKey)
        assertEquals(TestSecondTopLevelKey, navigationState.currentTopLevelKey)

        // remove TopLevel
        navigator.goBack()

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )

        assertEquals(TestKeyFirst, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun popMultipleNonTopLevel() {
        navigator.navigate(TestKeyFirst)
        navigator.navigate(TestKeySecond)

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
                TestKeyFirst,
                TestKeySecond,
            ),
            navigationState.currentSubStack,
        )

        navigator.goBack()
        navigator.goBack()

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
            ),
            navigationState.currentSubStack,
        )

        assertEquals(TestFirstTopLevelKey, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun popMultipleTopLevel() {
        // second sub-stack
        navigator.navigate(TestSecondTopLevelKey)
        navigator.navigate(TestKeyFirst)

        assertIterableEquals(
            listOf(
                TestSecondTopLevelKey,
                TestKeyFirst,
            ),
            navigationState.currentSubStack,
        )

        // third sub-stack
        navigator.navigate(TestThirdTopLevelKey)
        navigator.navigate(TestKeySecond)

        assertIterableEquals(
            listOf(
                TestThirdTopLevelKey,
                TestKeySecond,
            ),
            navigationState.currentSubStack,
        )

        repeat(4) {
            navigator.goBack()
        }

        assertIterableEquals(
            listOf(
                TestFirstTopLevelKey,
            ),
            navigationState.currentSubStack,
        )

        assertEquals(TestFirstTopLevelKey, navigationState.currentKey)
        assertEquals(TestFirstTopLevelKey, navigationState.currentTopLevelKey)
    }

    @Test
    fun throwOnEmptyBackStack() {
        assertThrows(IllegalStateException::class.java) {
            navigator.goBack()
        }
    }
}
