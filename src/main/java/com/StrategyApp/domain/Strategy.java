package com.StrategyApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="strategys")
@Getter @Setter @NoArgsConstructor
public class Strategy {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="strategy_id")
	private Integer strategyId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="description")
	private String description;
    
  	@Column(name="strategy_type")
	private String strategyType;
    
  	@Column(name="status")
	private String status;
    

}
