package com.example.candradinatha.committee.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.candradinatha.committee.model.Committee
import com.google.android.gms.tasks.Task
import org.jetbrains.anko.db.*

class SQLiteOpenHelper(ctx:Context): ManagedSQLiteOpenHelper(ctx, "TaskScheduler.db", null, 1) {

    companion object {
        private var instance: SQLiteOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SQLiteOpenHelper {
            if (instance == null) {
                instance = SQLiteOpenHelper(ctx.applicationContext)
            }
            return instance as SQLiteOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                Committee.TABLE_COMMITTEE, true,
                Committee.COMMITTEE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Committee.COMMITTEE_RAPAT to TEXT,
                Committee.COMMITTEE_DATE to TEXT,
                Committee.COMMITTEE_DESC to TEXT,
                Committee.COMMITTEE_FOTO to TEXT,
                Committee.COMMITTEE_NAME to TEXT,
                Committee.COMMITTEE_STATUS to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Committee.TABLE_COMMITTEE, true)
    }
}

val Context.database: SQLiteOpenHelper
    get() = SQLiteOpenHelper.getInstance(applicationContext)