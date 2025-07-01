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

    public Choice makeChoice(Choice choice) {
        Optional<Choice> existing = choiceRepository.findByUserIdAndLenzeId(
                choice.getUserId(), choice.getLenzeId()
        );

        if (existing.isPresent()) {
            throw new IllegalStateException("이미 선택한 항목입니다.");
        }

        return choiceRepository.save(choice);
    }

    public Optional<String> getSelectedValue(String userId, Long lenzeId) {
        return choiceRepository.findByUserIdAndLenzeId(userId, lenzeId)
                .map(Choice::getSelectedValue);
    }

    @Transactional
    public void cancelChoice(String userId, Long lenzeId) {
        Optional<Choice> existing = choiceRepository.findByUserIdAndLenzeId(userId, lenzeId);

        if (existing.isPresent()) {
            choiceRepository.deleteByUserIdAndLenzeId(userId, lenzeId);
        } else {
            throw new IllegalArgumentException("선택이 존재하지 않습니다.");
        }
    }

    public List<LenzeWithCountDto> findTop10LenzesByChoiceCount() {
        return choiceRepository.findTop10LenzesByChoiceCount(PageRequest.of(0, 10));
    }

    @Transactional
    public void deleteAllByLenzeId(Long lenzeId) {
        choiceRepository.deleteAllByLenzeId(lenzeId);
    }

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