package cmndtools.dipumba.com.util

import de.mkammerer.snowflakeid.SnowflakeIdGenerator

private const val generatorId = "123"

object IdGenerator {
    fun generateId(): Long{
        return SnowflakeIdGenerator.createDefault(generatorId.toInt()).next()
    }
}