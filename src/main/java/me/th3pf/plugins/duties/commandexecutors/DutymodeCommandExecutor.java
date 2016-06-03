package me.th3pf.plugins.duties.commandexecutors;

import me.th3pf.plugins.duties.Duties;
import me.th3pf.plugins.duties.ModeSwitcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class DutymodeCommandExecutor implements CommandExecutor
{
   @Override
   public boolean onCommand( CommandSender sender, Command command, String label, String[] args )
   {
      if( args.length == 0 )
      {
         try
         {
            //Own mode
            if( !sender.hasPermission( "duties.self.toggle" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.toggle" ) ) )
            {
               TellSender( sender, updates.MissingPermission, true );
               return true;
            }
            if( sender instanceof Player )
            {
               if( !Duties.Memories.containsKey( ( (Player) sender ).getUniqueId() ) )
                  TellSender( sender, updates.Enable, ModeSwitcher.EnableDutyMode( (Player) sender ) );
               else
                  TellSender( sender, updates.Disable, ModeSwitcher.DisableDutyMode( (Player) sender ) );

               return true;
            }
            else
            {
               Duties.GetInstance().LogMessage( "This command is only avaible for in-game player." );
               return true;
            }
         }
         catch( Exception ex )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + "An error occured while enabling duty mode." );
            return true;
         }
      }
      else if( args[0].equalsIgnoreCase( "toggle" ) )
      {
         try
         {
            //Set another players mode
            if( args.length >= 2 )
            {
               if( !sender.hasPermission( "duties.others.toggle" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.others.toggle" ) ) )
               {
                  TellSender( sender, updates.MissingPermission, true );
                  return true;
               }

               try
               {
                  if( Duties.GetInstance().getServer().getPlayer( args[1] ).isOnline() )
                  {
                     //Player is online
                  }
                  else
                  {
                     //Player is offline
                     TellSender( sender, updates.PlayerIsNotOnline, false );
                     return true;
                  }
               }
               catch( Exception exception )
               {
                  //Couldn't get player, player is offline
                  TellSender( sender, updates.PlayerIsNotOnline, false );
                  return true;
               }

               Player target = Bukkit.getPlayer( args[1] );

               if( !Duties.Memories.containsKey( target.getUniqueId() ) )
                  TellSender( sender, updates.EnableOfOther, ModeSwitcher.EnableDutyMode( target ) );
               else
                  TellSender( sender, updates.DisableOfOther, ModeSwitcher.DisableDutyMode( target ) );

               return true;
            }

            //Own mode
            if( sender instanceof Player )
            {
               if( !sender.hasPermission( "duties.self.toggle" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.toggle" ) ) )
               {
                  TellSender( sender, updates.MissingPermission, true );
                  return true;
               }

               if( !Duties.Memories.containsKey( ( (Player) sender ).getUniqueId() ) )
                  TellSender( sender, updates.Enable, ModeSwitcher.EnableDutyMode( (Player) sender ) );
               else
                  TellSender( sender, updates.Disable, ModeSwitcher.DisableDutyMode( (Player) sender ) );

               return true;
            }
            else
            {
               Duties.GetInstance().LogMessage( "This command is only avaible for in-game player." );
               return true;
            }
         }
         catch( Exception ex )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + "An error occured while enabling duty mode." );
            return true;
         }
      }
      else if( args[0].equalsIgnoreCase( "enable" ) || args[0].equalsIgnoreCase( "on" ) )
      {
         try
         {
            //Set another players mode
            if( args.length >= 2 )
            {
               if( !sender.hasPermission( "duties.others.enable" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.others.enable" ) ) )
               {
                  TellSender( sender, updates.MissingPermission, true );
                  return true;
               }

               try
               {
                  if( Duties.GetInstance().getServer().getPlayer( args[1] ).isOnline() )
                  {
                     //Player is online
                  }
                  else
                  {
                     //Player is offline
                     TellSender( sender, updates.PlayerIsNotOnline, false );
                     return true;
                  }
               }
               catch( Exception exception )
               {
                  //Couldn't get player, player is offline
                  TellSender( sender, updates.PlayerIsNotOnline, false );
                  return true;
               }

               if( Duties.Memories.containsKey( Bukkit.getPlayer( args[1] ).getUniqueId() ) )
               {
                  TellSender( sender, updates.AlreadyOn, false );
                  return true;
               }

               TellSender( sender, updates.EnableOfOther, ModeSwitcher.EnableDutyMode( Bukkit.getPlayer( args[1] ) ) );

               return true;
            }

            //Own mode
            if( !sender.hasPermission( "duties.self.enable" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.enable" ) ) )
            {
               TellSender( sender, updates.MissingPermission, true );
               return true;
            }
            if( sender instanceof Player )
            {
               if( !Duties.Memories.containsKey( ( (Player) sender ).getUniqueId() ) )
               {
                  TellSender( sender, updates.AlreadyOn, false );
                  return true;
               }

               TellSender( sender, updates.Enable, ModeSwitcher.EnableDutyMode( (Player) sender ) );

               return true;
            }
            else
            {
               TellSender( sender, updates.IngamePlayersOnly, false );
               return true;
            }
         }
         catch( Exception ex )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + "An error occured while enabling duty mode." );
            return true;
         }
      }
      else if( args[0].equalsIgnoreCase( "disable" ) || args[0].equalsIgnoreCase( "off" ) )
      {
         try
         {
            //Set another players mode
            if( args.length >= 2 )
            {
               if( !sender.hasPermission( "duties.others.disable" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.others.disable" ) ) )
               {
                  TellSender( sender, updates.MissingPermission, true );
                  return true;
               }

               try
               {
                  if( Duties.GetInstance().getServer().getPlayer( args[1] ).isOnline() )
                  {
                     //Player is online
                  }
                  else
                  {
                     //Player is offline
                     TellSender( sender, updates.PlayerIsNotOnline, false );
                     return true;
                  }
               }
               catch( Exception exception )
               {
                  //Couldn't get player, player is offline
                  TellSender( sender, updates.PlayerIsNotOnline, false );
                  return true;
               }

               if( !Duties.Memories.containsKey( Bukkit.getPlayer( args[1] ).getUniqueId() ) )
               {
                  TellSender( sender, updates.AlreadyOff, false );
                  return true;
               }

               TellSender( sender, updates.DisableOfOther, ModeSwitcher.DisableDutyMode( Duties.GetInstance().getServer().getPlayer( args[1] ).getPlayer() ) );

               return true;
            }

            //Own mode
            if( !sender.hasPermission( "duties.self.disable" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.disable" ) ) )
            {
               TellSender( sender, updates.MissingPermission, true );
               return true;
            }

            if( sender instanceof Player )
            {
               if( !Duties.Memories.containsKey( ( (Player) sender ).getUniqueId() ) )
               {
                  TellSender( sender, updates.AlreadyOff, false );
                  return true;
               }

               TellSender( sender, updates.Disable, ModeSwitcher.DisableDutyMode( (Player) sender ) );

               return true;
            }
            else
            {
               Duties.GetInstance().LogMessage( "This command is only avaible for in-game player." );
               return true;
            }
         }
         catch( Exception ex )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + "An error occured while disabling duty mode." );
            return true;
         }
      }
      else if( args[0].equalsIgnoreCase( "list" ) || args[0].equalsIgnoreCase( "who" ) )
      {
         if( !sender.hasPermission( "duties.list" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.list" ) ) )
         {
            TellSender( sender, updates.MissingPermission, true );
            return true;
         }

         String players = "";

         for( UUID playerId : Duties.Memories.keySet() )
         {
            Player player = Bukkit.getPlayer( playerId );

            if( !player.hasPermission( "duties.belisted" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( player, "duties.belisted" ) ) )
            {
               continue;
            }

            String formattedName = player.getName();
            if( Duties.Config.GetBoolean( "Vault.NameFormatting" ) && Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
            {
               formattedName = ( Duties.VaultAdapter.chat.getPlayerPrefix( player ) + formattedName + Duties.VaultAdapter.chat.getPlayerSuffix( player ) );
            }

            players += ( formattedName ) + ChatColor.WHITE + ", ";
         }

         if( players.length() <= 0 )
         {
            TellSender( sender, Duties.Messages.GetString( "Client.NoStaffOnDuty" ) );
         }
         else
         {
            players = players.substring( 0, players.length() - 2 );
            TellSender( sender, Duties.Messages.GetString( "Client.List" ) + players );
         }

         return true;
      }
      else if( args[0].equalsIgnoreCase( "listall" ) || args[0].equalsIgnoreCase( "whoall" ) )
      {
         if( !sender.hasPermission( "duties.listall" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.listall" ) ) )
         {
            TellSender( sender, updates.MissingPermission, true );
            return true;
         }

         String players = "";

         for( UUID playerId : Duties.Memories.keySet() )
         {
            //if(!player.hasPermission("duties.belisted"))continue;
            Player player = Bukkit.getPlayer( playerId );

            String formattedName = player.getName();
            if( Duties.Config.GetBoolean( "Vault.NameFormatting" ) && Duties.GetInstance().getServer().getPluginManager().isPluginEnabled( "Vault" ) )
            {
               formattedName = ( Duties.VaultAdapter.chat.getPlayerPrefix( player ) + formattedName + Duties.VaultAdapter.chat.getPlayerSuffix( player ) );
            }

            players += ( formattedName ) + ChatColor.WHITE + ", ";
         }

         if( players.length() <= 0 )
         {
            TellSender( sender, Duties.Messages.GetString( "Client.NoPlayersOnDuty" ) );
         }
         else
         {
            players = players.substring( 0, players.length() - 2 );
            TellSender( sender, Duties.Messages.GetString( "Client.ListAll" ) + players );
         }

         return true;
      }
      else if( args[0].equalsIgnoreCase( "showbroadcast" ) || args[0].equalsIgnoreCase( "showb" ) )
      {
         //Set another players mode
         if( args.length >= 2 )
         {
            if( !sender.hasPermission( "duties.others.setbroadcasts.shown" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.others.setbroadcasts.shown" ) ) )
            {
               TellSender( sender, updates.MissingPermission, true );
               return true;
            }

            try
            {
               if( Duties.GetInstance().getServer().getPlayer( args[1] ).isOnline() )
               {
                  //Player is online
               }
               else
               {
                  //Player is offline
                  TellSender( sender, updates.PlayerIsNotOnline, false );
                  return true;
               }
            }
            catch( Exception exception )
            {
               //Couldn't get player, player is offline
               TellSender( sender, updates.PlayerIsNotOnline, false );
               return true;
            }

            if( !Duties.Hidden.contains( Duties.GetInstance().getServer().getPlayer( args[1] ).getPlayer() ) )
            {
               TellSender( sender, updates.BroadcastsAlreadyShownForPlayer, false );
               return true;
            }

            Duties.Hidden.remove( Duties.GetInstance().getServer().getPlayer( args[1] ).getPlayer() );

            TellSender( sender, updates.BroadcastsShownForPlayer, true );
            return true;
         }

         //Set own status
         if( !sender.hasPermission( "duties.self.setbroadcasts.shown" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.setbroadcasts.shown" ) ) )
         {
            TellSender( sender, updates.MissingPermission, true );
            return true;
         }
         if( sender instanceof Player )
         {
            if( !Duties.Hidden.contains( sender ) )
            {
               TellSender( sender, updates.BroadcastsAlreadyShown, false );
               return true;
            }

            Duties.Hidden.remove( sender );
         }
         else
         {
            Duties.GetInstance().LogMessage( "This command is only avaible for in-game player." );
            return true;
         }

         TellSender( sender, updates.BroadcastsShown, true );
         return true;
      }
      else if( args[0].equalsIgnoreCase( "hidebroadcast" ) || args[0].equalsIgnoreCase( "hideb" ) )
      {
         //Set another players mode
         if( args.length >= 2 )
         {
            if( !sender.hasPermission( "duties.others.setbroadcasts.hidden" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.others.setbroadcasts.hidden" ) ) )
            {
               TellSender( sender, updates.MissingPermission, true );
               return true;
            }

            try
            {
               if( Duties.GetInstance().getServer().getPlayer( args[1] ).isOnline() )
               {
                  //Player is online
               }
               else
               {
                  //Player is offline
                  TellSender( sender, updates.PlayerIsNotOnline, false );
                  return true;
               }
            }
            catch( Exception exception )
            {
               //Couldn't get player, player is offline
               TellSender( sender, updates.PlayerIsNotOnline, false );
               return true;
            }

            if( Duties.Hidden.contains( Duties.GetInstance().getServer().getPlayer( args[1] ).getPlayer() ) )
            {
               TellSender( sender, updates.BroadcastsAlreadyHiddenForPlayer, false );
               return true;
            }

            Duties.Hidden.add( Duties.GetInstance().getServer().getPlayer( args[1] ).getPlayer() );

            TellSender( sender, updates.BroadcastsHiddenForPlayer, true );
            return true;
         }

         //Set own status
         if( !sender.hasPermission( "duties.self.setbroadcasts.hidden" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.self.setbroadcasts.hidden" ) ) )
         {
            TellSender( sender, updates.MissingPermission, true );
            return true;
         }
         if( sender instanceof Player )
         {
            if( Duties.Hidden.contains( sender ) )
            {
               TellSender( sender, updates.BroadcastsAlreadyHidden, false );
               return true;
            }

            Duties.Hidden.add( (Player) sender );
         }
         else
         {
            Duties.GetInstance().LogMessage( "This command is only avaible for in-game player." );
            return true;
         }

         TellSender( sender, updates.BroadcastsHidden, true );
         return true;
      }
      else if( args[0].equalsIgnoreCase( "purge" ) )
      {
         if( !sender.hasPermission( "duties.purge" ) && !( Duties.Config.GetBoolean( "Vault.Permissions" ) && Duties.VaultAdapter.permission.has( sender, "duties.purge" ) ) )
         {
            TellSender( sender, updates.MissingPermission, true );
            return true;
         }

         ArrayList<UUID> keySet = new ArrayList<>();
         keySet.addAll( Duties.Memories.keySet() );

         for( UUID playerId : keySet )
         {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer( playerId );

            if( !offlinePlayer.isOnline() )
            {
               Duties.GetInstance().LogMessage( "Player " + offlinePlayer.getName() + "(" + offlinePlayer.getUniqueId() + ") is offline and can therefore not be put off duty mode." );
               continue;
            }

            if( !ModeSwitcher.DisableDutyMode( offlinePlayer.getPlayer() ) )
            {
               Duties.GetInstance().LogMessage( "Couldn't disable duty mode for " + offlinePlayer.getName() + "(" + offlinePlayer.getUniqueId() + ")." );
            }
         }

         TellSender( sender, updates.Purged, true );

         return true;
      }
      else
      {
         TellSender( sender, updates.CommandExtensionNotFound, false );
         return true;
      }
   }

   private enum updates
   {
      Enable, Disable, EnableOfOther, DisableOfOther, PlayerIsNotOnline, Purged, MissingPermission, CommandExtensionNotFound, AlreadyOn, AlreadyOff, IngamePlayersOnly, BroadcastsShown, BroadcastsHidden, BroadcastsShownForPlayer, BroadcastsHiddenForPlayer, BroadcastsAlreadyShown, BroadcastsAlreadyHidden, BroadcastsAlreadyShownForPlayer, BroadcastsAlreadyHiddenForPlayer, Exception
   }

   private void TellSender( CommandSender sender, updates update, boolean success )
   {

      if( update == updates.Enable )
      {
         if( !( sender instanceof Player ) )
         {
            return;
         }
         if( success )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Enabled" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
         else
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Fail.Enable" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
      }
      else if( update == updates.Disable )
      {
         if( !( sender instanceof Player ) )
         {
            return;
         }
         if( success )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Disabled" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
         else
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Fail.Disable" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
      }
      if( update == updates.EnableOfOther )
      {
         if( !( sender instanceof Player ) )
         {
            return;
         }
         if( success )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.EnabledForOtherPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
         else
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Fail.EnableForOtherPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
      }
      if( update == updates.DisableOfOther )
      {
         if( !( sender instanceof Player ) )
         {
            return;
         }
         if( success )
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.DisabledForOtherPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
         else
         {
            sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Fail.DisableForOtherPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
         }
      }
      else if( update == updates.PlayerIsNotOnline )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.PlayerNotOnline" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.Purged )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.Purged" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.MissingPermission )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.MissingPermission" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.CommandExtensionNotFound )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.CommandExtensionNotFound" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.AlreadyOn )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.AlreadyOn" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.AlreadyOff )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.AlreadyOff" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.IngamePlayersOnly )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.IngamePlayersOnly" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsShown )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsShown" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsHidden )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsHidden" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsShownForPlayer )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsShownForPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsHiddenForPlayer )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsHiddenForPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsAlreadyShown )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsAlreadyShown" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsAlreadyHidden )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsAlreadyHidden" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsAlreadyShownForPlayer )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsAlreadyShownForPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.BroadcastsAlreadyHiddenForPlayer )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.BroadcastsAlreadyHiddenForPlayer" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
      else if( update == updates.Exception )
      {
         sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + Duties.Messages.GetString( "Client.ErrorOccured" ).replaceAll( "$", "" ).replaceAll( "&", "" ) );
      }
   }

   public void TellSender( CommandSender sender, String message )
   {
      sender.sendMessage( Duties.Messages.GetString( "Client.Tag" ) + message.replaceAll( "$", "" ).replaceAll( "&", "" ) );
   }
}
