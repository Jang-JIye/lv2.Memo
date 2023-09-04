package com.sparta.lv1memo.controller;

import com.sparta.lv1memo.dto.MemoRequestDto;
import com.sparta.lv1memo.dto.MemoResponseDto;
import com.sparta.lv1memo.entity.Memo;
import com.sparta.lv1memo.security.UserDetailsImpl;
import com.sparta.lv1memo.service.MemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    //create
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.createMemo(requestDto, userDetails.getUser());
    }

    //readAll
    @GetMapping("/memos")
    public List<MemoResponseDto> getAllMemo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.getMemos(userDetails.getUser());
    }

    //read
    @GetMapping("/memos/{id}")
    public Memo getMemo(@PathVariable Long id) {
        return memoService.getMemo(id);
    }

    //update
    @PutMapping("/memos/{id}")
    public ResponseEntity<String> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id, requestDto);
    }

    //delete
    @DeleteMapping("/memos/{id}")
    public ResponseEntity<String> deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.deleteMemo(id, requestDto);
    }
}
