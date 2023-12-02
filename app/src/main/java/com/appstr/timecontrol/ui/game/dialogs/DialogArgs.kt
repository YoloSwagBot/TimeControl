package com.appstr.timecontrol.ui.game.dialogs

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.TimeControl
import java.io.Serializable


class DialogArgsSetPlayerTime(
    val gameState: GameState?,
    val player: Player
): Serializable { companion object { val key = "DialogArgsSetPlayerTime" } }

class DialogArgsAskCancelCurrentGame(
    val timeControlToSet: TimeControl
): Serializable { companion object { val key = "DialogArgsCheckCancelCurrentGame" } }