//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    IO.println(String.format("Hello and welcome!"));

    for (int i = 1; i <= 5; i++) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        IO.println("i = " + i);
        /**
         * Move the given minion 1 step in the direction if legal.
         * Cost: 1 budget (deducted even if move fails), but only if budget >= 1.
         */
        /**
         * Shoot 1 step in the direction.
         * Cost: expenditure + 1 (only deducted if affordable).
         * If target occupied, damage = max(1, expenditure - defense).
         * If victim hp < 1 after hit: remove from map + state.
         */
    }
}
