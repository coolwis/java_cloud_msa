package com.psc.cloud.recommend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection="recommends")
@CompoundIndex(name ="prod-rec-id", unique=true, def="{'productId':1, 'recommandId':1}")
public class RecommendEntity {

	
	@Id
	private Integer id; 
	@Version
	private Integer version;
	private int productId;
	private int recommandId;
	private String author;
	private String content;
	
	public RecommendEntity(int productId, int recommandId, String author, String content) {
		this.productId =productId; 
		this.recommandId = recommandId; 
		this.author= author;
		this.content  = content;
	}
}
