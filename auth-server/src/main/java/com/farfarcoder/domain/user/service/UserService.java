package com.farfarcoder.domain.user.service;

import com.farfarcoder.domain.user.entity.UserEntity;
import com.farfarcoder.domain.user.mapper.UserMapper;
import com.farfarcoder.domain.user.model.User;
import com.farfarcoder.domain.user.repository.UserRepository;
import com.farfarcoder.domain.user.util.EncryptionUtil;
import com.farfarcoder.exception.ApiException;
import com.farfarcoder.type.ServiceErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findByUserId(String userId){
        UserEntity userEntity= userRepository.findOneWithUserByUserId(userId).orElseThrow(
            () -> new ApiException(ServiceErrorType.NOT_FOUND)
        );
        return userMapper.toModel(userEntity);
    }

    public User findById(Long id){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(
                    ()-> new ApiException(ServiceErrorType.NOT_FOUND)
                );
        return userMapper.toModel(userEntity);
    }

    public void checkUserExistence(String userId){
        if(userRepository.findOneWithUserByUserId(userId).orElse(null) !=null){
            throw new ApiException(ServiceErrorType.DUPLICATE_ENTRY);
        }
    }

    @Transactional
    public User createUser(User user){
        String encryptedValue = null;   //암호화 처리 필요
        try{
            if(user.idType().name().equals("REG_NO")){
                encryptedValue = EncryptionUtil.encrypt(user.idValue(), EncryptionUtil.SALT_REG_NO);
            }else{
                encryptedValue = EncryptionUtil.encrypt(user.idValue(), EncryptionUtil.SALT_CORP_NO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userMapper.toModel(
            userRepository.save(userMapper.toEntity(user,encryptedValue))
        );
    }

}
