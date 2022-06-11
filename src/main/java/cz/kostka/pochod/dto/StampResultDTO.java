package cz.kostka.pochod.dto;

import cz.kostka.pochod.domain.Stamp;
import cz.kostka.pochod.enums.StampSubmitStatus;

/**
 * Created by dkostka on 2/6/2022.
 */
public class StampResultDTO {
    private StampSubmitStatus stampSubmitStatus;

    public StampResultDTO(final StampSubmitStatus stampSubmitStatus) {
        this.stampSubmitStatus = stampSubmitStatus;
    }

    public StampSubmitStatus getStampSubmitStatus() {
        return stampSubmitStatus;
    }

    public void setStampSubmitStatus(final StampSubmitStatus stampSubmitStatus) {
        this.stampSubmitStatus = stampSubmitStatus;
    }
}
