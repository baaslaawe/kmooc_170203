package com.nile.kmooc.model.course;

import android.support.annotation.Nullable;

import com.nile.kmooc.model.db.DownloadEntry;
import com.nile.kmooc.module.storage.IStorage;

public interface HasDownloadEntry {
    @Nullable
    DownloadEntry getDownloadEntry(IStorage storage);
}
