package com.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Anki {
	
	private Integer id;
	private String word;
	private String meaning;
	private int learned;

}
