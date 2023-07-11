package back.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MeasuredValue {

    @Id
    @GeneratedValue
    @Column(name = "VALUE_ID", updatable = false, nullable = false, unique = true)
    private Long MID;

    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double humidity;

    @Column(nullable = false)
    private Double waterLevel;

    @Column(nullable = false)
    private Double ph;

    @Column(nullable = false)
    private Double turbidity;
}
