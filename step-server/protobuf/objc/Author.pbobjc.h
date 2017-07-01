// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Author.proto

// This CPP symbol can be defined to use imports that match up to the framework
// imports needed when using CocoaPods.
#if !defined(GPB_USE_PROTOBUF_FRAMEWORK_IMPORTS)
 #define GPB_USE_PROTOBUF_FRAMEWORK_IMPORTS 0
#endif

#if GPB_USE_PROTOBUF_FRAMEWORK_IMPORTS
 #import <Protobuf/GPBProtocolBuffers.h>
#else
 #import "GPBProtocolBuffers.h"
#endif

#if GOOGLE_PROTOBUF_OBJC_VERSION < 30002
#error This file was generated by a newer version of protoc which is incompatible with your Protocol Buffer library sources.
#endif
#if 30002 < GOOGLE_PROTOBUF_OBJC_MIN_SUPPORTED_VERSION
#error This file was generated by an older version of protoc which is incompatible with your Protocol Buffer library sources.
#endif

// @@protoc_insertion_point(imports)

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wdeprecated-declarations"

CF_EXTERN_C_BEGIN

@class PBUser;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Enum PBUserRegisterType

typedef GPB_ENUM(PBUserRegisterType) {
  /**
   * Value used if any message's field encounters a value that is not defined
   * by this enum. The message will also have C functions to get/set the rawValue
   * of the field.
   **/
  PBUserRegisterType_GPBUnrecognizedEnumeratorValue = kGPBUnrecognizedEnumeratorValue,
  PBUserRegisterType_Local = 0,
  PBUserRegisterType_Weixin = 1,
  PBUserRegisterType_Qq = 2,
};

GPBEnumDescriptor *PBUserRegisterType_EnumDescriptor(void);

/**
 * Checks to see if the given value is defined by the enum or was not known at
 * the time this source was generated.
 **/
BOOL PBUserRegisterType_IsValidValue(int32_t value);

#pragma mark - AuthorRoot

/**
 * Exposes the extension registry for this file.
 *
 * The base class provides:
 * @code
 *   + (GPBExtensionRegistry *)extensionRegistry;
 * @endcode
 * which is a @c GPBExtensionRegistry that includes all the extensions defined by
 * this file and all files that it depends on.
 **/
@interface AuthorRoot : GPBRootObject
@end

#pragma mark - PBUser

typedef GPB_ENUM(PBUser_FieldNumber) {
  PBUser_FieldNumber_Id_p = 1,
  PBUser_FieldNumber_Type = 2,
  PBUser_FieldNumber_Identity = 3,
  PBUser_FieldNumber_NickName = 4,
  PBUser_FieldNumber_Sex = 5,
  PBUser_FieldNumber_HeadIcon = 7,
};

@interface PBUser : GPBMessage

@property(nonatomic, readwrite) int32_t id_p;

/** 1=本地账号 2=微信 3=QQ */
@property(nonatomic, readwrite) int32_t type;

/** 手机号码、（微信、QQ id） */
@property(nonatomic, readwrite, copy, null_resettable) NSString *identity;

@property(nonatomic, readwrite, copy, null_resettable) NSString *nickName;

/** 0=男 2=女 */
@property(nonatomic, readwrite) int32_t sex;

@property(nonatomic, readwrite, copy, null_resettable) NSString *headIcon;

@end

#pragma mark - PBUserAdd

typedef GPB_ENUM(PBUserAdd_FieldNumber) {
  PBUserAdd_FieldNumber_User = 1,
  PBUserAdd_FieldNumber_Md5Password = 2,
  PBUserAdd_FieldNumber_ValidCode = 3,
};

/**
 * 本地用户注册
 **/
@interface PBUserAdd : GPBMessage

@property(nonatomic, readwrite, strong, null_resettable) PBUser *user;
/** Test to see if @c user has been set. */
@property(nonatomic, readwrite) BOOL hasUser;

@property(nonatomic, readwrite, copy, null_resettable) NSString *md5Password;

@property(nonatomic, readwrite, copy, null_resettable) NSString *validCode;

@end

#pragma mark - PBUserThirdAdd

typedef GPB_ENUM(PBUserThirdAdd_FieldNumber) {
  PBUserThirdAdd_FieldNumber_User = 1,
  PBUserThirdAdd_FieldNumber_AccessToken = 3,
  PBUserThirdAdd_FieldNumber_RefreshToken = 4,
};

/**
 * 第三方用户注册，微信 和QQ
 **/
@interface PBUserThirdAdd : GPBMessage

@property(nonatomic, readwrite, strong, null_resettable) PBUser *user;
/** Test to see if @c user has been set. */
@property(nonatomic, readwrite) BOOL hasUser;

@property(nonatomic, readwrite, copy, null_resettable) NSString *accessToken;

@property(nonatomic, readwrite, copy, null_resettable) NSString *refreshToken;

@end

NS_ASSUME_NONNULL_END

CF_EXTERN_C_END

#pragma clang diagnostic pop

// @@protoc_insertion_point(global_scope)