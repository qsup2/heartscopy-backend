package com.heartscopy.heartsocpyBeckEnd.service.lenzeservice;

import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeSearchResultDto;
import com.heartscopy.heartsocpyBeckEnd.repository.lenzerepository.LenzeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LenzeService {
    private final LenzeRepository lenzeRepository;


    public Lenze createLenze(Lenze lenze, String uid) {

        lenze.setWriterId(uid);
        return lenzeRepository.save(lenze);
    }


    public Lenze findById(Long id) {
        return lenzeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 렌즈가 존재하지 않습니다: " + id));
    }


    @Transactional
    public void deleteById(Long id, String uid) {
        Lenze lenze = findById(id);
        if (!lenze.getWriterId().equals(uid)) {
            throw new SecurityException("권한이 없습니다.");
        }
        lenzeRepository.deleteById(id);
    }


    public Page<Lenze> getLenzesByPage(int page, int size) {
        return lenzeRepository.findAll(PageRequest.of(page, size));
    }


    public List<LenzeSearchResultDto> searchLenzes(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Lenze> resultPage = lenzeRepository.findByTopicContainingOrderByCreatedAtDesc(keyword, pageable);
        return resultPage.map(l -> new LenzeSearchResultDto(l.getLenzeId(), l.getTopic()))
                .getContent();
    }


    public List<Lenze> getRecentLenzes() {
        return lenzeRepository.findTop10ByOrderByCreatedAtDesc();
    }
}