package clients.models;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class ClientPassportData {
    private int series;
    private int number;

}