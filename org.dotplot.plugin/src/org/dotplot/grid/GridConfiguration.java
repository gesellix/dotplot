/*
 * Created on 01.07.2004
 */
package org.dotplot.grid;

import java.io.Serializable;

/**
 * Container for settings of the grid module.
 *
 * @author Tobias Gesellchen
 */
public class GridConfiguration implements Serializable
{
   private boolean enableGrid;
   private int serverPort;

   private String mediatorAddress;
   private int mediatorPort;

   private transient GridClient gridClient;
   private transient GridServer gridServer;

   private transient boolean enableNotification = false;
   private transient boolean notifyAttachImage;
   private transient String notifySMTPHost;
   private transient String notifySMTPUser;
   private transient String notifySMTPPass;
   private transient String notifyEmailFrom;
   private transient String[] notifyEmailTo;
   private transient String notifyEmailSubject;

   /**
    * Creates a basic configuration for the grid module and uses the given settings.
    *
    * @param enableGrid      a boolean -- true if the grid is enabled
    * @param serverPort      an int specifying the port of the server
    * @param mediatorAddress a String containg the server address, the client wants to connect to
    * @param mediatorPort    an int specifying the port of the server
    */
   public GridConfiguration(boolean enableGrid, int serverPort, String mediatorAddress, int mediatorPort)
   {
      setEnableGrid(enableGrid);
      setServerPort(serverPort);
      setMediatorAddress(mediatorAddress);
      setMediatorPort(mediatorPort);
   }

   /**
    * did the user activate the grid?
    *
    * @return true, if the grid is enabled.
    */
   public boolean isGridEnabled()
   {
      return enableGrid;
   }

   /**
    * enable the grid.
    *
    * @param enableGrid enable/disable
    */
   public void setEnableGrid(boolean enableGrid)
   {
      this.enableGrid = enableGrid;
   }

   /**
    * Is the grid enabled and a gridServer running?
    *
    * @return true if this object is grid active
    */
   public boolean isGridActive()
   {
      return isGridEnabled()
            && ((gridServer != null && gridServer.isRunning())
//            || (gridClient != null && gridClient.isRunning())
      );
   }

   /**
    * returns the port of the server.
    *
    * @return the port of the server
    *
    * @see #setServerPort(int)
    */
   public int getServerPort()
   {
      return serverPort;
   }

   /**
    * sets the port of the server.
    *
    * @param serverPort the port of the server
    *
    * @see #getServerPort()
    */
   public void setServerPort(int serverPort)
   {
      this.serverPort = serverPort;
   }

   /**
    * returns the address of the server, the client wants to connect to.
    *
    * @return the address of the server
    *
    * @see #setMediatorAddress(String)
    */
   public String getMediatorAddress()
   {
      return mediatorAddress;
   }

   /**
    * sets the address the client wants to connect to.
    *
    * @param mediatorAddress the address of the server
    *
    * @see #getMediatorAddress()
    */
   public void setMediatorAddress(String mediatorAddress)
   {
      this.mediatorAddress = mediatorAddress;
   }

   /**
    * the port, the client wants to connect to.
    *
    * @return the port.
    *
    * @see #setMediatorPort(int)
    */
   public int getMediatorPort()
   {
      return mediatorPort;
   }

   /**
    * sets the port, the client wants to connect to.
    *
    * @param mediatorPort the port
    *
    * @see #getMediatorPort()
    */
   public void setMediatorPort(int mediatorPort)
   {
      this.mediatorPort = mediatorPort;
   }

   /**
    * the current locally created GridClient.
    *
    * @return the GridClient
    *
    * @see #setGridClient(GridClient)
    */
   public GridClient getGridClient()
   {
      return gridClient;
   }

   /**
    * sets the local GridClient.
    *
    * @param gridClient the client
    *
    * @see #getGridClient()
    */
   public void setGridClient(GridClient gridClient)
   {
      this.gridClient = gridClient;
   }

   /**
    * the GridServer.
    *
    * @return the server
    *
    * @see #setGridServer(GridServer)
    */
   public GridServer getGridServer()
   {
      return gridServer;
   }

   /**
    * sets the GridServer.
    *
    * @param gridServer the server
    *
    * @see #getGridServer()
    */
   public void setGridServer(GridServer gridServer)
   {
      this.gridServer = gridServer;
   }

   public boolean isEnableNotification()
   {
      return enableNotification;
   }

   public void setEnableNotification(boolean enableNotification)
   {
      this.enableNotification = enableNotification;
   }

   public boolean isNotifyAttachImage()
   {
      return notifyAttachImage;
   }

   public void setNotifyAttachImage(boolean notifyAttachImage)
   {
      this.notifyAttachImage = notifyAttachImage;
   }

   public String getNotifyEmailFrom()
   {
      return notifyEmailFrom;
   }

   public void setNotifyEmailFrom(String notifyEmailFrom)
   {
      this.notifyEmailFrom = notifyEmailFrom;
   }

   public String getNotifyEmailSubject()
   {
      return notifyEmailSubject;
   }

   public void setNotifyEmailSubject(String notifyEmailSubject)
   {
      this.notifyEmailSubject = notifyEmailSubject;
   }

   public String[] getNotifyEmailTo()
   {
      return notifyEmailTo;
   }

   public void setNotifyEmailTo(String[] notifyEmailTo)
   {
      this.notifyEmailTo = notifyEmailTo;
   }

   public String getNotifySMTPHost()
   {
      return notifySMTPHost;
   }

   public void setNotifySMTPHost(String notifySMTPHost)
   {
      this.notifySMTPHost = notifySMTPHost;
   }

   public String getNotifySMTPPass()
   {
      return notifySMTPPass;
   }

   public void setNotifySMTPPass(String notifySMTPPass)
   {
      this.notifySMTPPass = notifySMTPPass;
   }

   public String getNotifySMTPUser()
   {
      return notifySMTPUser;
   }

   public void setNotifySMTPUser(String notifySMTPUser)
   {
      this.notifySMTPUser = notifySMTPUser;
   }
}
