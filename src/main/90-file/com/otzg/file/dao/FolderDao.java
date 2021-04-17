package com.otzg.file.dao;

import com.otzg.file.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderDao extends JpaRepository<Folder,Integer> {
}
