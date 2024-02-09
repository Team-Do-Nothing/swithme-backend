package com.donothing.swithme.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberStudy is a Querydsl query type for MemberStudy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberStudy extends EntityPathBase<MemberStudy> {

    private static final long serialVersionUID = -1075784741L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberStudy memberStudy = new QMemberStudy("memberStudy");

    public final EnumPath<ApproveStatus> approveStatus = createEnum("approveStatus", ApproveStatus.class);

    public final DateTimePath<java.time.LocalDateTime> dateOkay = createDateTime("dateOkay", java.time.LocalDateTime.class);

    public final QMember member;

    public final NumberPath<Long> memberStudyId = createNumber("memberStudyId", Long.class);

    public final QStudy study;

    public QMemberStudy(String variable) {
        this(MemberStudy.class, forVariable(variable), INITS);
    }

    public QMemberStudy(Path<? extends MemberStudy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberStudy(PathMetadata metadata, PathInits inits) {
        this(MemberStudy.class, metadata, inits);
    }

    public QMemberStudy(Class<? extends MemberStudy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.study = inits.isInitialized("study") ? new QStudy(forProperty("study"), inits.get("study")) : null;
    }

}

