package model.characters.combat

import kotlin.math.max
import kotlin.math.min

/**
 * Get result of combat between two combat characters.
 */
fun combat(character1: CombatCharacter, character2: CombatCharacter): CombatResult {
    val hpReduce1 = min(character1.getHp(), max(1, character2.getAttack() - character1.getArmor()))
    val hpReduce2 = min(character2.getHp(), max(1, character1.getAttack() - character2.getArmor()))
    return CombatResult(hpReduce1, hpReduce2)
}

data class CombatResult(val hpReduce1: Int, val hpReduce2: Int)

/**
 * Change characters' stats according to combat result.
 */
fun applyCombatResults(character1: CombatCharacter, character2: CombatCharacter, result: CombatResult) {
    character1.reduceHp(result.hpReduce1)
    character2.reduceHp(result.hpReduce2)
}