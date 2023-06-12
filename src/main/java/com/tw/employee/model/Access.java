package com.tw.employee.model;

import java.util.List;

import com.tw.ai.model.AiLocations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Access {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accessId;
	private String accessName;

}
