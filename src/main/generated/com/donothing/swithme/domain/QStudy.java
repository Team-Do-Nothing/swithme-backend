package com.donothing.swithme.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -1135077547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudy study = new QStudy("study");

    public final DateTimePath<java.time.LocalDateTime> dateStudyEnd = createDateTime("dateStudyEnd", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dateStudyStart = createDateTime("dateStudyStart", java.time.LocalDateTime.class);

    public final QMember member;

    public final NumberPath<Integer> numberOfMembers = createNumber("numberOfMembers", Integer.class);

    public final NumberPath<Long> studyId = createNumber("studyId", Long.class);

    public final StringPath studyInfo = createString("studyInfo");

    public final EnumPath<StudyStatus> studyStatus = createEnum("studyStatus", StudyStatus.class);

    public final EnumPath<StudyType> studyType = createEnum("studyType", StudyType.class);

    public final StringPath title = createString("title");

    public QStudy(String variable) {
        this(Study.class, forVariable(variable), INITS);
    }

    public QStudy(Path<? extends Study> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudy(PathMetadata metadata, PathInits inits) {
        this(Study.class, metadata, inits);
    }

    public QStudy(Class<? extends Study> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

