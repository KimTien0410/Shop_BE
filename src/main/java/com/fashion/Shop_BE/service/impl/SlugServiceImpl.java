package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.service.SlugService;
import com.github.slugify.Slugify;
import org.springframework.stereotype.Component;

@Component
public class SlugServiceImpl implements SlugService {
    @Override
    public String generateSlug(String name) {
        Slugify slugify = new Slugify();
        return slugify.slugify(name);
    }
}
