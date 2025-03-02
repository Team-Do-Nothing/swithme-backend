package com.donothing.swithme.common;

import com.donothing.swithme.domain.Category;
import com.donothing.swithme.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CategoryInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (categoryRepository.count() == 0) { // 데이터 중복 방지
            Category programming = categoryRepository.save(new Category("프로그래밍", null));
            Category certification = categoryRepository.save(new Category("자격증", null));
            Category language = categoryRepository.save(new Category("어학", null));
            Category interview = categoryRepository.save(new Category("면접", null));

            // 하위 카테고리 추가
            categoryRepository.save(new Category("웹 개발", programming));
            categoryRepository.save(new Category("모바일 앱 개발", programming));
            categoryRepository.save(new Category("게임 개발", programming));
            categoryRepository.save(new Category("코딩테스트", programming));

            categoryRepository.save(new Category("IT", certification));
            categoryRepository.save(new Category("금융", certification));
            categoryRepository.save(new Category("보건", certification));
            categoryRepository.save(new Category("역사", certification));
            categoryRepository.save(new Category("공학", certification));

            categoryRepository.save(new Category("영어", language));
            categoryRepository.save(new Category("중국어", language));
            categoryRepository.save(new Category("일본어", language));
            categoryRepository.save(new Category("독일어", language));
            categoryRepository.save(new Category("프랑스어", language));

            categoryRepository.save(new Category("공기업", interview));
            categoryRepository.save(new Category("금융권", interview));
            categoryRepository.save(new Category("공학계열", interview));
            categoryRepository.save(new Category("어문계열", interview));

            System.out.println("✅ 기본 카테고리 데이터 삽입 완료!");
        }
    }
}

