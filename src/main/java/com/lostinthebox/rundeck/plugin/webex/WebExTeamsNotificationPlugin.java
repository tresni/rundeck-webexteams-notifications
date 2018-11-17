package com.lostinthebox.rundeck.plugin.webex;

import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;

import com.ciscospark.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.net.URI;
import java.util.*;
import java.io.StringWriter;
import java.io.IOException;

@Plugin(name="WebExTeams", service="Notification")
@PluginDescription(title="WebEx Teams Plugin", description="A plugin to send notifications to WebEx Teams")
public class WebExTeamsNotificationPlugin implements NotificationPlugin {

    @PluginProperty(
            title = "Room",
            description = "Set the WebEx Team room to send notification messages to.",
            required = true)
    private String room;

    @PluginProperty(
            title = "Access Token",
            description = "Access Token to post notifications with.",
            required = true,
            scope = PropertyScope.Project)
    private String token;


    public boolean postNotification(String trigger, Map executionData, Map config) {
        Map<String, Object> model = new HashMap();
        model.put("trigger", trigger);
        model.put("execution", executionData);
        model.put("config", config);

        Spark spark = Spark.builder()
            .baseUrl(URI.create("https://api.ciscospark.com/v1"))
            .accessToken(token)
            .build();

        Configuration freeMarkerCfg = new Configuration();
        StringWriter md = new StringWriter();
        StringWriter txt = new StringWriter();
        freeMarkerCfg.setClassForTemplateLoading(WebExTeamsNotificationPlugin.class, "/templates");

        try {
            Template template = freeMarkerCfg.getTemplate("notification.md.ftl");
            template.process(model, md);
            template = freeMarkerCfg.getTemplate("notification.txt.ftl");
            template.process(model, txt);
        }
        catch (IOException io) {
            txt.write("Error loading template [" + io.getMessage() + "]");
        }
        catch (TemplateException te) {
            txt.write("Error loading template [" + te.getMessage() + "]");
        }


        Message m = new Message();
        m.setRoomId(room);
        if (md.getBuffer().length() != 0)
            m.setMarkdown(md.toString());
        m.setText(txt.toString());
        double version = Double.parseDouble(System.getProperty("java.specification.version"));
        if (version <= 1.5) {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1");
        }
        spark.messages().post(m);
        return true;
    }


}
