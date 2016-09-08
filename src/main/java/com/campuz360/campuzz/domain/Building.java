package com.campuz360.campuzz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Building.
 */
@Entity
@Table(name = "building")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "building")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Size(max = 3400000)
    @Lob
    @Column(name = "building_cover_image")
    private byte[] buildingCoverImage;

    @Column(name = "building_cover_image_content_type")
    private String buildingCoverImageContentType;

    @Size(max = 2100000)
    @Lob
    @Column(name = "building_image_1")
    private byte[] buildingImage1;

    @Column(name = "building_image_1_content_type")
    private String buildingImage1ContentType;

    @Size(max = 2100000)
    @Lob
    @Column(name = "building_image_2")
    private byte[] buildingImage2;

    @Column(name = "building_image_2_content_type")
    private String buildingImage2ContentType;

    @Size(max = 2100000)
    @Lob
    @Column(name = "building_image_3")
    private byte[] buildingImage3;

    @Column(name = "building_image_3_content_type")
    private String buildingImage3ContentType;

    @Column(name = "building_established_year")
    private Integer buildingEstablishedYear;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public byte[] getBuildingCoverImage() {
        return buildingCoverImage;
    }

    public void setBuildingCoverImage(byte[] buildingCoverImage) {
        this.buildingCoverImage = buildingCoverImage;
    }

    public String getBuildingCoverImageContentType() {
        return buildingCoverImageContentType;
    }

    public void setBuildingCoverImageContentType(String buildingCoverImageContentType) {
        this.buildingCoverImageContentType = buildingCoverImageContentType;
    }

    public byte[] getBuildingImage1() {
        return buildingImage1;
    }

    public void setBuildingImage1(byte[] buildingImage1) {
        this.buildingImage1 = buildingImage1;
    }

    public String getBuildingImage1ContentType() {
        return buildingImage1ContentType;
    }

    public void setBuildingImage1ContentType(String buildingImage1ContentType) {
        this.buildingImage1ContentType = buildingImage1ContentType;
    }

    public byte[] getBuildingImage2() {
        return buildingImage2;
    }

    public void setBuildingImage2(byte[] buildingImage2) {
        this.buildingImage2 = buildingImage2;
    }

    public String getBuildingImage2ContentType() {
        return buildingImage2ContentType;
    }

    public void setBuildingImage2ContentType(String buildingImage2ContentType) {
        this.buildingImage2ContentType = buildingImage2ContentType;
    }

    public byte[] getBuildingImage3() {
        return buildingImage3;
    }

    public void setBuildingImage3(byte[] buildingImage3) {
        this.buildingImage3 = buildingImage3;
    }

    public String getBuildingImage3ContentType() {
        return buildingImage3ContentType;
    }

    public void setBuildingImage3ContentType(String buildingImage3ContentType) {
        this.buildingImage3ContentType = buildingImage3ContentType;
    }

    public Integer getBuildingEstablishedYear() {
        return buildingEstablishedYear;
    }

    public void setBuildingEstablishedYear(Integer buildingEstablishedYear) {
        this.buildingEstablishedYear = buildingEstablishedYear;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        if(building.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, building.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Building{" +
            "id=" + id +
            ", buildingName='" + buildingName + "'" +
            ", buildingCoverImage='" + buildingCoverImage + "'" +
            ", buildingCoverImageContentType='" + buildingCoverImageContentType + "'" +
            ", buildingImage1='" + buildingImage1 + "'" +
            ", buildingImage1ContentType='" + buildingImage1ContentType + "'" +
            ", buildingImage2='" + buildingImage2 + "'" +
            ", buildingImage2ContentType='" + buildingImage2ContentType + "'" +
            ", buildingImage3='" + buildingImage3 + "'" +
            ", buildingImage3ContentType='" + buildingImage3ContentType + "'" +
            ", buildingEstablishedYear='" + buildingEstablishedYear + "'" +
            ", location='" + location + "'" +
            '}';
    }
}
