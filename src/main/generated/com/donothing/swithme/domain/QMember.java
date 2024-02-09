package com.donothing.swithme.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1013534066L;

    public static final QMember member = new QMember("member1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath authority = createString("authority");

    public final DatePath<java.time.LocalDate> birthdate = createDate("birthdate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> dateCreated = _super.dateCreated;

    public final DateTimePath<java.time.LocalDateTime> dateWithdraw = createDateTime("dateWithdraw", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final EnumPath<GenderType> gender = createEnum("gender", GenderType.class);

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<LoginType> loginType = createEnum("loginType", LoginType.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final NumberPath<Double> temperature = createNumber("temperature", Double.class);

    public final BooleanPath withdraw = createBoolean("withdraw");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

