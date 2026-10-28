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
@Table(name="strategy_parameters")
@Getter @Setter @NoArgsConstructor
public class StrategyParameter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="strategy_parameter_id")
	private Integer strategyParameterId;
    
  	@Column(name="strategy_id")
	private Integer strategyId;
    
  	@Column(name="parameter_name")
	private String parameterName;
    
  	@Column(name="parameter_value")
	private String parameterValue;
    
  	@Column(name="data_type")
	private String dataType;
    

}
