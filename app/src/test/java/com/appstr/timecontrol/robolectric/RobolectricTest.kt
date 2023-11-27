package com.appstr.timecontrol.robolectric



//@RunWith(RobolectricTestRunner::class)
//class RobolectricTest {
//    @get:Rule val composeTestRule = createComposeRule()
//
//
//    @Before
//    fun setup(){
////        ShadowLog.stream = System.out
//    }
//
//    @Test
//    fun `onClick setup time screen button, show SetupTimeScreen`(){
//        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
//            scenario.onActivity {
//                composeTestRule.onNodeWithTag("SetupTimeScreen icon").performClick()
//                composeTestRule.onNodeWithTag("SetupTimeScreen container").assertIsDisplayed()
//            }
//        }
//    }
//
//    @Test
//    fun `set new game if current game is null, check time labels and move labels`(){
//        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
//            scenario.onActivity {
//                composeTestRule.onNodeWithTag("SetupTimeScreen icon").performClick()
//                composeTestRule.onNodeWithTag("SetupTimeScreen container").assertIsDisplayed()
//                composeTestRule.onNodeWithText("Setup Game").performClick()
//
//                composeTestRule.onNodeWithTag("Player 1 time label", true).assertTextEquals("10:00")
//                composeTestRule.onNodeWithTag("Player 2 time label", true).assertTextEquals("10:00")
//
//                composeTestRule.onNodeWithTag("Player 1 move label", true).assertTextEquals("0")
//                composeTestRule.onNodeWithTag("Player 2 move label", true).assertTextEquals("0")
//            }
//        }
//    }
//
//    @OptIn(ExperimentalTestApi::class)
//    @Test
//    fun `check if cancelling current game dialog pops up and works as expected`(){
//        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
//            scenario.onActivity {
//                // Set a game
//                composeTestRule.onNodeWithTag("SetupTimeScreen icon").performClick()
//                composeTestRule.onNodeWithTag("SetupTimeScreen container").assertIsDisplayed()
//                composeTestRule.onNodeWithText("Setup Game").performClick()
//
//                // Set a new game and check if dialog appears
//                composeTestRule.onNodeWithTag("SetupTimeScreen icon").performClick()
//                // select first game(1 minute)
//                composeTestRule.onAllNodes(hasTestTag("SetupTimeScreen_ListItem"))[0].performClick()
////                composeTestRule.onNodeWithText("Setup Game").performClick()
//
//
//                composeTestRule.onRoot(true).printToString(100)
//                // check if cancel current game dialog appears
////                composeTestRule.onAllNodesWithTag("CancelGameDialog_Confirm", true)[0].assertIsDisplayed()
////                // check if cancel button on dialog works
////                composeTestRule.onNodeWithText("Cancel").performClick()
////                composeTestRule.onAllNodesWithTag("CancelGameDialog_Confirm", true)[0].assertIsNotDisplayed()
////                // check if confirm button works in dialog
////                composeTestRule.onNodeWithText("Setup Game").performClick()
////                composeTestRule.waitUntilAtLeastOneExists(hasTestTag("DialogCheckCancelCurrentGame"))
////                composeTestRule.onNodeWithTag("DialogCheckCancelCurrentGame", true).assertIsDisplayed()
////                composeTestRule.onNodeWithText("Confirm").performClick()
////
////
////                // check if the new game was set correctly
////                composeTestRule.onNodeWithTag("Player 1 time label", true).assertTextEquals("1:00")
////                composeTestRule.onNodeWithTag("Player 2 time label", true).assertTextEquals("1:00")
////
////                composeTestRule.onNodeWithTag("Player 1 move label", true).assertTextEquals("0")
////                composeTestRule.onNodeWithTag("Player 2 move label", true).assertTextEquals("0")
//            }
//        }
//
//    }
//
//}