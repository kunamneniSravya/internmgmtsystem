package com.finalproject.internMgmtSystem.repository;

import java.util.List;
import com.finalproject.internMgmtSystem.dto.UserBatchDto;

public interface UserBatchDao {
    List<UserBatchDto> getUsersByTrainerId(int trainerId);
}
