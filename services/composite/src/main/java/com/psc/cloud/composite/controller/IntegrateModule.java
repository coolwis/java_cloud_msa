package com.psc.cloud.composite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psc.cloud.api.controller.ProductControllerInterface;
import com.psc.cloud.api.controller.RecommendControllerInterface;
import com.psc.cloud.api.controller.ReviewControllerInterface;
import com.psc.cloud.api.dto.Product;
import com.psc.cloud.api.dto.Recommend;
import com.psc.cloud.api.dto.Review;
import com.psc.cloud.api.exception.InvalidInputException;
import com.psc.cloud.api.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IntegrateModule
		implements ProductControllerInterface, RecommendControllerInterface, ReviewControllerInterface {

	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;
	private final String productServiceUrl;
	private final String recommendServiceUrl;
	private final String reviewServiceUrl;

	public IntegrateModule(RestTemplate restTemplate, ObjectMapper mapper,
			@Value("${app.product.protocol}") String productProtocal, @Value("${app.product.host}") String productHost,
			@Value("${app.product.port}") int productPort, @Value("${app.product.service}") String productService,

			@Value("${app.recommend.protocol}") String recommendProtocal,
			@Value("${app.recommend.host}") String recommendHost, @Value("${app.recommend.port}") int recommendPort,
			@Value("${app.recommend.service}") String recommendService,

			@Value("${app.review.protocol}") String reviewProtocal, @Value("${app.review.host}") String reviewHost,
			@Value("${app.review.port}") int reviewPort, @Value("${app.review.service}") String reviewService

	) {
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.productServiceUrl = productProtocal = "://" + productHost + ":" + productPort + "/" + productService;
		this.recommendServiceUrl = recommendProtocal = "://" + recommendHost + ":" + recommendPort + "/"
				+ recommendService;
		this.reviewServiceUrl = reviewProtocal = "://" + reviewHost + ":" + reviewPort + "/" + reviewService;
	}

	private RuntimeException httpClientException(HttpClientErrorException ex) {
		switch (ex.getStatusCode()) {
		case NOT_FOUND:
			return new NotFoundException(ex.getResponseBodyAsString());
		case UNPROCESSABLE_ENTITY:
			return new InvalidInputException(ex.getResponseBodyAsString());
		default:
			log.warn("get a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
			log.warn("error body: {}", ex.getResponseBodyAsString());
			return ex;
		}
	}

	@Override
	public Review createReview(Review body) {

		return null;
	}

	@Override
	public List<Review> getReviews(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReviews(int productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Recommend createRecommend(Recommend body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recommend> getRecommends(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecommends(int productId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product createProduct(Product body) {
		try {

			String url = productServiceUrl;
			Product product = restTemplate.postForObject(url, body, Product.class);
			return product;
		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}

	}

	@Override
	public Product getProduct(int productId) { 
		try {
			String url = productServiceUrl + "/" + productId;
			Product product = restTemplate.getForObject(url, Product.class);
			return product;
		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}
	}

	@Override
	public void deleteProduct(int productId) {
		try {
			String url = productServiceUrl + "/" + productId;
			restTemplate.delete(url);
		
		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}

	}

}
