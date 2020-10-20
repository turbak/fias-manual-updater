package ru.digital.league.manualfias.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(schema = "fiasupdate", name = "tbfias_empty_coordinates")
public class FiasEmptyCoordinates {

    @Id
    @Column(name = "guid")
    public String guid;

}
