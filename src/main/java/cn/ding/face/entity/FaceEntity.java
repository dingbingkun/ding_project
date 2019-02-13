package cn.ding.face.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "face")
public class FaceEntity {
    private long id;
    private String name;
    private byte[] faceFeature;
    private String path;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "face_feature", nullable = true)
    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }

    @Basic
    @Column(name = "path", nullable = true, length = 150)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaceEntity that = (FaceEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Arrays.equals(faceFeature, that.faceFeature) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, path);
        result = 31 * result + Arrays.hashCode(faceFeature);
        return result;
    }
}
