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
@Table(name="strategy_versions")
@Getter @Setter @NoArgsConstructor
public class StrategyVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="strategy_version_id")
	private Integer strategyVersionId;
    
  	@Column(name="strategy_id")
	private Integer strategyId;
    
  	@Column(name="version_number")
	private String versionNumber;
    
  	@Column(name="created_at")
	private Date createdAt;
    
  	@Column(name="status")
	private String status;
    

}
