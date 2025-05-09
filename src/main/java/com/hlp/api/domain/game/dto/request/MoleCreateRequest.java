package com.hlp.api.domain.game.dto.request;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import static com.hlp.api.domain.game.model.GameCategory.CATCH_MOLE;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hlp.api.domain.game.model.Game;
import com.hlp.api.domain.game.model.MoleCatch;
import com.hlp.api.domain.game.model.Result;
import com.hlp.api.domain.user.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(value = SnakeCaseStrategy.class)
public record MoleCreateRequest(
    @Schema(description = "결과", example = "SUCCESS", requiredMode = REQUIRED)
    @NotNull(message = "결과를 입력해주세요.")
    Result result,

    @Schema(description = "점수", example = "1234", requiredMode = REQUIRED)
    @NotNull(message = "점수를 입력해주세요.")
    Integer score,

    @Schema(description = "체력", example = "55", requiredMode = REQUIRED)
    @NotNull(message = "체력을 입력해주세요.")
    Integer hp,

    @Schema(description = "잡은 두더지 수", example = "54", requiredMode = REQUIRED)
    @NotNull(message = "잡은 두더지 수를 입력해주세요.")
    Integer moleCatchCount
) {
    public Game toGame(User user) {
        return Game.builder()
            .user(user)
            .result(result)
            .gameCategory(CATCH_MOLE)
            .isDeleted(false)
            .build();
    }

    public MoleCatch toMoleCatch(Game game) {
        return MoleCatch.builder()
            .hp(hp)
            .score(score)
            .moleCatchCount(moleCatchCount)
            .game(game)
            .build();
    }
}
