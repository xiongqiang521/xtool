package com.xq.xtool.controller;

import com.xq.xtool.entity.ProductEntity;
import com.xq.xtool.mapper.ProductMapper;
import com.xq.xtool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ProductController {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @RequestMapping("list")
    public Mono<List<ProductEntity>> listProduct() {
        List<ProductEntity> productEntities = productMapper.findAll();
        return Mono.just(productEntities);
    }

    @RequestMapping("product")
    public Flux<ProductEntity> getListProduct() {
        List<ProductEntity> productEntities = productMapper.findAll();
        return Flux.fromIterable(productEntities);
    }

    @RequestMapping("sent")
    public Flux<ServerSentEvent<ProductEntity>> sentEventFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<ProductEntity>builder()
                        .id(String.valueOf(seq))
                        .data(productService.get())
                        .build());
    }

    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }

    @RequestMapping("save")
    public Mono<ProductEntity> saveProduct() {
        return Mono.just(productService.get());
    }
}
