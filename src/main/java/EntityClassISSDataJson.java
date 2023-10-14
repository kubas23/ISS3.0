import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ISS_data")
public class EntityClassISSDataJson {
    @Data
    public class IssPosition {
        @Id
        @SerializedName("longitude")
        private String longitudeWithSerializedName;
        @SerializedName("latitude")
        private String latitudeWithSerializedName;
    }

    @Id
    private IssPosition iss_position;
    private String message;
    private long timestamp;
}