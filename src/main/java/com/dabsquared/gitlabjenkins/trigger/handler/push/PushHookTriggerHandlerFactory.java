package com.dabsquared.gitlabjenkins.trigger.handler.push;

import com.dabsquared.gitlabjenkins.trigger.TriggerOpenMergeRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robin Müller
 */
public final class PushHookTriggerHandlerFactory {

    private PushHookTriggerHandlerFactory() {}

    public static PushHookTriggerHandler newPushHookTriggerHandler(boolean triggerOnPush,
                                                                   TriggerOpenMergeRequest triggerOpenMergeRequestOnPush,
                                                                   boolean skipWorkInProgressMergeRequest,
                                                                   String mrSkipUsers) {
        if (triggerOnPush || triggerOpenMergeRequestOnPush == TriggerOpenMergeRequest.both) {
            return new PushHookTriggerHandlerList(retrieveHandlers(triggerOnPush, triggerOpenMergeRequestOnPush, skipWorkInProgressMergeRequest, mrSkipUsers));
        } else {
            return new NopPushHookTriggerHandler();
        }
    }

    private static List<PushHookTriggerHandler> retrieveHandlers(boolean triggerOnPush,
                                                                 TriggerOpenMergeRequest triggerOpenMergeRequestOnPush,
                                                                 boolean skipWorkInProgressMergeRequest,
                                                                 String mrSkipUsers) {
        List<PushHookTriggerHandler> result = new ArrayList<>();
        if (triggerOnPush) {
            result.add(new PushHookTriggerHandlerImpl());
        }
        if (triggerOpenMergeRequestOnPush == TriggerOpenMergeRequest.both) {
            result.add(new OpenMergeRequestPushHookTriggerHandler(skipWorkInProgressMergeRequest,
                                                                  mrSkipUsers));
        }
        return result;
    }
}
