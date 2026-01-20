package dev.gedfalk.astonproject.service;

import dev.gedfalk.astonproject.dto.UserEventDto;
import dev.gedfalk.astonproject.dto.UserRequestDto;
import dev.gedfalk.astonproject.dto.UserResponseDto;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.kafka.UserEventProducer;
import dev.gedfalk.astonproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    // преобразуем Entity в Dto
    private UserResponseDto convertToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {

        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .age(requestDto.getAge())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        UserEventDto event = UserEventDto.builder()
                .eventType(UserEventDto.EventType.USER_CREATED)
                .email(savedUser.getEmail())
                .userName(savedUser.getName())
                .userId(savedUser.getId())
                .eventTimestamp(LocalDateTime.now())
                .build();

        userEventProducer.sendUserEvent(event);

        return convertToDto(savedUser);
    }

    public UserResponseDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким id не найден"));
        return convertToDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Integer id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("Пользователь с таким id не найден");
//        }
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Пользователь с таким id не найден"));

        // нужно ли сохранить email, name?...

        userRepository.deleteById(id);

        UserEventDto event = UserEventDto.builder()
                .eventType(UserEventDto.EventType.USER_DELETED)
                .email(user.getEmail())
                .userName(user.getName())
                .userId(id)
                .eventTimestamp(LocalDateTime.now())
                .build();

        userEventProducer.sendUserEvent(event);
    }

    @Transactional
    public UserResponseDto updateUser(Integer id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким id не найдет"));

        String newEmail = requestDto.getEmail();
//        if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
//            throw new IllegalArgumentException("Такой email уже есть");
//        }

        user.setName(requestDto.getName());
        user.setEmail(newEmail);
        user.setAge(requestDto.getAge());

        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }
}
