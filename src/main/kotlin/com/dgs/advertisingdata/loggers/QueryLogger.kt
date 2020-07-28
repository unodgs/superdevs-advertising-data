package com.dgs.advertisingdata.loggers

import org.jdbi.v3.core.statement.SqlLogger
import org.jdbi.v3.core.statement.StatementContext
import org.slf4j.LoggerFactory
import java.sql.SQLException

class QueryLogger constructor(private val logEveryQuery: Boolean = false): SqlLogger {
    private val log = LoggerFactory.getLogger(javaClass)
    
    override fun logBeforeExecution(context: StatementContext?) {
        if (logEveryQuery) {
            log.debug("\nsql: {},\nbindings: {}\n\n",
                context?.renderedSql,
                context?.binding.toString())
        }
    }
    
    override fun logException(context: StatementContext?, ex: SQLException?) {
        log.error("\nsql: {},\n" + "bindings: {}\nerror: {}\n\n",
            context?.renderedSql,
            context?.binding.toString(),
            ex)
    }
}