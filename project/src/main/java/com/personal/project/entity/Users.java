package com.personal.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



//  @Data is a shortcut annotation that combines the features of @ToString , @EqualsAndHashCode , @Getter @Setter , 
// and @RequiredArgsConstructor together. So, @Data generates all the boilerplate involved in POJOs (Plain Old Java Objects).

@Data
@Getter
@Setter


@Entity
@Table(name="users",uniqueConstraints= {
		@UniqueConstraint(columnNames= {"email"})
})
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="email",nullable=false)
	private String email;
	
	@Column(name="password",nullable=false)
	private String password;
	

}
