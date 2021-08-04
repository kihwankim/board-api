package com.cnu.spg.board.domain;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("N")
public class NotificationBoard extends Board {
    private boolean isAlert;
}
