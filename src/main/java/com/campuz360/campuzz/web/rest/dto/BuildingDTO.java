package com.campuz360.campuzz.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the Building entity.
 */
public class BuildingDTO implements Serializable {

    private Long id;

    @NotNull
    private String buildingName;

    @Size(max = 3400000)
    @Lob
    private byte[] buildingCoverImage;

    private String buildingCoverImageContentType;
    @Size(max = 2100000)
    @Lob
    private byte[] buildingImage1;

    private String buildingImage1ContentType;
    @Size(max = 2100000)
    @Lob
    private byte[] buildingImage2;

    private String buildingImage2ContentType;
    @Size(max = 2100000)
    @Lob
    private byte[] buildingImage3;

    private String buildingImage3ContentType;
    private Integer buildingEstablishedYear;

    @NotNull
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

        BuildingDTO buildingDTO = (BuildingDTO) o;

        if ( ! Objects.equals(id, buildingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + id +
            ", buildingName='" + buildingName + "'" +
            ", buildingCoverImage='" + buildingCoverImage + "'" +
            ", buildingImage1='" + buildingImage1 + "'" +
            ", buildingImage2='" + buildingImage2 + "'" +
            ", buildingImage3='" + buildingImage3 + "'" +
            ", buildingEstablishedYear='" + buildingEstablishedYear + "'" +
            ", location='" + location + "'" +
            '}';
    }
}
