package ru.azaytsev.votingrestaurants.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.azaytsev.votingrestaurants.config.AppConfig;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheCleaner {

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = AppConfig.MENUS_CACHE, allEntries = true)
    public void clearCache() {
        log.info("Cleaning menus cache.");
    }
}
