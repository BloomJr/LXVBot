class ReminderType(
    val name: String,
    val aliases: List<String>,
    val cooldownMS: Long,
    val patreonAffected: Boolean,
    val responseName: ReminderType.(List<String>) -> String = { this.name },
    val validator: (List<String>) -> Boolean = { true }
) {
}