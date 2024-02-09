package com.donothing.swithme.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberChallenge is a Querydsl query type for MemberChallenge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberChallenge extends EntityPathBase<MemberChallenge> {

    private static final long serialVersionUID = 1100671349L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberChallenge memberChallenge = new QMemberChallenge("memberChallenge");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QChallenge challenge;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> dateCreated = _super.dateCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public final NumberPath<Long> memberChalId = createNumber("memberChalId", Long.class);

    public QMemberChallenge(String variable) {
        this(MemberChallenge.class, forVariable(variable), INITS);
    }

    public QMemberChallenge(Path<? extends MemberChallenge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberChallenge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberChallenge(PathMetadata metadata, PathInits inits) {
        this(MemberChallenge.class, metadata, inits);
    }

    public QMemberChallenge(Class<? extends MemberChallenge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.challenge = inits.isInitialized("challenge") ? new QChallenge(forProperty("challenge"), inits.get("challenge")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

