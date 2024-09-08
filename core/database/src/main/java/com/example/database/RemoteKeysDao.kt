package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.entities.CursorRemoteKeys
import com.example.database.model.entities.RemoteKeys


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCursors(remoteKey: List<CursorRemoteKeys>)

    @Query("SELECT * FROM cursor_remote_keys WHERE cursorId = :id")
    suspend fun remoteKeysCursors(id: String): CursorRemoteKeys?

    @Query("DELETE FROM cursor_remote_keys")
    suspend fun clearRemoteCursors()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE keyId = :id")
    suspend fun remoteKeys(id: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
