package com.psc.cloud.composite.controller;

//import java.net.http.HttpHeaders;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import static org.springframework.http.HttpMethod.GET;

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
		this.productServiceUrl = productProtocal + "://" + productHost + ":" + productPort + "/" + productService;
		this.recommendServiceUrl = recommendProtocal + "://" + recommendHost + ":" + recommendPort + "/"
				+ recommendService;
		this.reviewServiceUrl = reviewProtocal + "://" + reviewHost + ":" + reviewPort + "/" + reviewService;
		
		log.debug("IntegrateModule product url:{}", this.productServiceUrl);
		log.debug("IntegrateModule recommend url:{}", this.recommendServiceUrl);
		log.debug("IntegrateModule review url:{}", this.reviewServiceUrl);
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
		try {

			String url = reviewServiceUrl;
			HttpHeaders headers =new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			log.debug("create review url:{}", url);
			log.debug("create review body:{}", body.toString());

			HttpEntity<Review> entity =new HttpEntity<>(body, headers);

			Review review= restTemplate.postForObject(url, entity, Review.class);
			return review;
		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}
	}

	@Override
	public List<Review> getReviews(int productId) {
		try {
			String url = reviewServiceUrl + "/" + productId;
			ParameterizedTypeReference<List<Review>> parameterizedTypeReference= new ParameterizedTypeReference<List<Review>>() {
				@Override
				public Type getType() {
					return super.getType();
				}
			};
			ResponseEntity<List<Review>> reviews= restTemplate.exchange(url, GET, null, parameterizedTypeReference);
			return reviews.getBody();
		} catch (HttpClientErrorException ex) {
			log.warn("getReviews exception , {}", ex.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public void deleteReviews(int productId) {
		// TODO Auto-generated method stub
		try {
			String url = reviewServiceUrl + "/" + productId;
			restTemplate.delete(url);

		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}
	}

	@Override
	public Recommend createRecommend(Recommend body) {
		try {

			String url = recommendServiceUrl;
			HttpHeaders headers =new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			log.debug("create recommend url:{}", url);
			log.debug("create recommend body:{}", body.toString());

			HttpEntity<Recommend> entity =new HttpEntity<>(body, headers);

			Recommend recommend = restTemplate.postForObject(url, entity, Recommend.class);
			return recommend;
		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}
	}

	@Override
	public List<Recommend> getRecommends(int productId) {
		try {
			String url = recommendServiceUrl + "/" + productId;
			ParameterizedTypeReference<List<Recommend>> parameterizedTypeReference= new ParameterizedTypeReference<List<Recommend>>() {
				@Override
				public Type getType() {
					return super.getType();
				}
			};
			ResponseEntity<List<Recommend>> recommends= restTemplate.exchange(url, GET, null, parameterizedTypeReference);
			return recommends.getBody();
		} catch (HttpClientErrorException ex) {
			log.warn("getRecommends exception , {}", ex.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public void deleteRecommends(int productId) {
		// TODO Auto-generated method stub
		try {
			String url = recommendServiceUrl + "/" + productId;
			restTemplate.delete(url);

		} catch (HttpClientErrorException ex) {
			throw httpClientException(ex);
		}
	}

	@Override
	public Product createProduct(Product body) {
		try {

			String url = productServiceUrl;
			HttpHeaders headers =new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			log.debug("createProduct url:{}", url);
			log.debug("createProduct body:{}", body.toString());

			HttpEntity<Product> entity =new HttpEntity<>(body, headers);

 			Product product = restTemplate.postForObject(url, entity, Product.class);
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
