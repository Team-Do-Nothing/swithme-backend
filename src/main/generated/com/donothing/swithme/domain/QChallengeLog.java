package com.donothing.swithme.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChallengeLog is a Querydsl query type for ChallengeLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChallengeLog extends EntityPathBase<ChallengeLog> {

    private static final long serialVersionUID = 1509789909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChallengeLog challengeLog = new QChallengeLog("challengeLog");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final NumberPath<Long> chalLogId = createNumber("chalLogId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> dateCreated = _super.dateCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMemberChallenge memberChallenge;

    public final StringPath s3Url = createString("s3Url");

    public QChallengeLog(String variable) {
        this(ChallengeLog.class, forVariable(variable), INITS);
    }

    public QChallengeLog(Path<? extends ChallengeLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChallengeLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChallengeLog(PathMetadata metadata, PathInits inits) {
        this(ChallengeLog.class, metadata, inits);
    }

    public QChallengeLog(Class<? extends ChallengeLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberChallenge = inits.isInitialized("memberChallenge") ? new QMemberChallenge(forProperty("memberChallenge"), inits.get("memberChallenge")) : null;
    }

}

