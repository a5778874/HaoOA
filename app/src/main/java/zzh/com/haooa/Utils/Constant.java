package zzh.com.haooa.Utils;

/**
 * Created by ZZH on 2018/2/7.
 */

public class Constant {
    public static final String IS_NEW_INVITE = "is_new_invite";// 新邀请标记

    public enum InvitationStatus {
        // 联系人邀请信息状态
        NEW_INVITE,// 新邀请
        INVITE_ACCEPT,//接受邀请
        INVITE_ACCEPT_BY_PEER,// 邀请被接受

        // --以下是群组邀请信息状态--

        //收到邀请去加入群
        NEW_GROUP_INVITE,

        //收到申请群加入
        NEW_GROUP_APPLICATION,

        //群邀请已经被对方接受
        GROUP_INVITE_ACCEPTED,

        //群申请已经被批准
        GROUP_APPLICATION_ACCEPTED,

        //接受了群邀请
        GROUP_ACCEPT_INVITE,

        //批准的群加入申请
        GROUP_ACCEPT_APPLICATION,

        //拒绝了群邀请
        GROUP_REJECT_INVITE,

        //拒绝了群申请加入
        GROUP_REJECT_APPLICATION,

        //群邀请被对方拒绝
        GROUP_INVITE_DECLINED,

        //群申请被拒绝
        GROUP_APPLICATION_DECLINED
    }

    public static final String CONTACT_CHANGED = "contact_changed";// 发送联系人变化的广播
    public static final String CONTACT_INVITE_CHANGED = "contact_invite_changed";// 联系人邀请信息变化的广播
    public static final String GROUP_INVITE_CHANGED = "group_invite_changed";// 群邀请信息变化的广播


    //请假申请状态
    public static final int APPLYING = 0;   //待审批
    public static final int APPLY_SUCCESS = 1;  //同意
    public static final int APPLY_FAILURE = -1; //不同意

    //角色相关
    public static final String ROLE_ADMIN = "1005";
    public static final String ROLE_NONE = "1000";
}
