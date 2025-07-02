package com.heartscopy.heartsocpyBeckEnd.service.choiceservice;

import com.heartscopy.heartsocpyBeckEnd.domain.choice.Choice;
import com.heartscopy.heartsocpyBeckEnd.dto.choice.MyChoiceDto;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeWithCountDto;
import com.heartscopy.heartsocpyBeckEnd.dto.mate.LenzeChoiceDto;
import com.heartscopy.heartsocpyBeckEnd.mapper.ChoiceMapper;
import com.heartscopy.heartsocpyBeckEnd.repository.choicerepository.ChoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final ChoiceMapper choiceMapper;

    /**
     * 새로운 선택 생성
     * userId는 컨트롤러에서 인증된 UID로 설정된 상태여야 함.
     */
    public Choice makeChoice(Choice choice) {
        Optional<Choice> existing = choiceRepository.findByUserIdAndLenzeId(
                choice.getUserId(), choice.getLenzeId()
        );

        if (existing.isPresent()) {
            throw new IllegalStateException("이미 선택한 항목입니다.");
        }

        return choiceRepository.save(choice);
    }

    /**
     * 특정 사용자와 렌즈에 대해 선택된 값 조회
     * @param userId 인증된 UID가 전달됨
     */
    public Optional<String> getSelectedValue(String userId, Long lenzeId) {
        return choiceRepository.findByUserIdAndLenzeId(userId, lenzeId)
                .map(Choice::getSelectedValue);
    }

    /**
     * 특정 사용자와 렌즈 선택 취소
     * @param userId 인증된 UID
     */
    @Transactional
    public void cancelChoice(String userId, Long lenzeId) {
        Optional<Choice> existing = choiceRepository.findByUserIdAndLenzeId(userId, lenzeId);

        if (existing.isPresent()) {
            choiceRepository.deleteByUserIdAndLenzeId(userId, lenzeId);
        } else {
            throw new IllegalArgumentException("선택이 존재하지 않습니다.");
        }
    }

    /**
     * 인기 렌즈 상위 10개 반환
     */
    public List<LenzeWithCountDto> findTop10LenzesByChoiceCount() {
        return choiceRepository.findTop10LenzesByChoiceCount(PageRequest.of(0, 10));
    }

    /**
     * 렌즈 ID로 모든 선택 삭제
     */
    @Transactional
    public void deleteAllByLenzeId(Long lenzeId) {
        choiceRepository.deleteAllByLenzeId(lenzeId);
    }

    /**
     * 사용자와 매칭되는 다른 사용자 목록과 매칭 횟수 반환
     * @param userId 인증된 UID
     */
    public Map<String, Integer> findMatchingUsers(String userId) {
        List<LenzeChoiceDto> userChoices = choiceMapper.findChoicesByUserId(userId);

        if (userChoices.isEmpty()) return Collections.emptyMap();

        List<String> matchingUserIds = choiceMapper.findMatchingUserIds(userChoices, userId);

        Map<String, Integer> result = new HashMap<>();
        for (String matchedUser : matchingUserIds) {
            result.put(matchedUser, result.getOrDefault(matchedUser, 0) + 1);
        }

        return result;
    }

    /**
     * 특정 사용자의 선택 내역 반환
     * @param userId 인증된 UID
     */
    public List<MyChoiceDto> getChoicesByUserId(String userId) {
        List<Choice> choices = choiceRepository.findByUserIdOrderByChooseAtDesc(userId);
        return choices.stream()
                .map(choice -> new MyChoiceDto(
                        choice.getLenzeId(),
                        choice.getTopic(),
                        choice.getSelectedValue(),
                        choice.getChooseAt(),
                        choice.getA(),
                        choice.getB()
                ))
                .collect(Collectors.toList());
    }
}