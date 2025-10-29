# CRC Cards — Event Lottery System App (Grouped by Role)

Each class lists Responsibilities ↔ Collaborators in one concise table.

============================================================
ENTRANT-FACING
============================================================

## MainActivity (entrant context)
| Responsibilities | Collaborator(s) |
|---|---|
| Launch entrant flow and global navigation | EventListActivity, EntrantActivity |
| Request runtime permissions (camera, location, notifications) | GeolocationService, QRCodeService, NotificationController |
| Route results from QR scanner and pickers | QRCodeActivity, ImageStorageController |

## EventListActivity (Event Browser)
| Responsibilities | Collaborator(s) |
|---|---|
| Display and refresh list of events | EventController |
| Apply filters and sorting | FilterController |
| Show availability windows and distance | ScheduleController, GeolocationService |
| Navigate to EventActivity | EventActivity |

## EventActivity (entrant view)
| Responsibilities | Collaborator(s) |
|---|---|
| Display event details, poster, capacity | EventController, ImageStorageController |
| Show registration window state | ScheduleController |
| Join or view waiting list | WaitingListController, EntrantController |
| Show location/map and distance | GeolocationService |
| Launch QR view or scan flow | QRCodeActivity |

## EntrantActivity
| Responsibilities | Collaborator(s) |
|---|---|
| Display and edit entrant profile | EntrantController |
| Show joined events and invites | WaitingListController, InviteController |
| Manage notification and privacy settings | NotificationController, AuthenticationService |

## EntrantController
| Responsibilities | Collaborator(s) |
|---|---|
| Load/save entrant profile | ProfileDB |
| Join or leave waiting lists | WaitingListController, EventController |
| Accept or decline invites | InviteController, RegistrationController |
| Update device binding | AuthenticationService |

## InviteActivity (entrant)
| Responsibilities | Collaborator(s) |
|---|---|
| Show invite status and expiry | InviteController |
| Accept or decline invite | EntrantController |
| Navigate to registration on accept | RegistrationActivity |

============================================================
ORGANIZER-FACING
============================================================

## OrganizerActivity
| Responsibilities | Collaborator(s) |
|---|---|
| Create or edit events and posters | OrganizerController, ImageStorageController |
| Configure registration window and rules | ScheduleController |
| Run selections and notify winners | LotteryService, NotificationController |
| View waiting list and geomap | WaitingListController, GeolocationService |

## OrganizerController
| Responsibilities | Collaborator(s) |
|---|---|
| Persist organizer event changes | EventDB |
| Enforce capacity and geofence settings | WaitingListController, GeolocationService |
| Trigger selection rounds | LotteryService, SelectionRoundController |
| Broadcast organizer messages | NotificationController |

## RegistrationActivity (organizer)
| Responsibilities | Collaborator(s) |
|---|---|
| Display confirmed registrants | RegistrationController |
| Handle cancellations by entrants | EntrantController |
| Export final roster | CSVExportController |

## RegistrationController
| Responsibilities | Collaborator(s) |
|---|---|
| Confirm accepted invites into roster | InviteController |
| Process cancellations and replacements | SelectionRoundController |
| Persist roster and status | RegistrationDB |
| Provide exportable lists | CSVExportController |

============================================================
ADMINISTRATOR-FACING
============================================================

## AdministratorActivity
| Responsibilities | Collaborator(s) |
|---|---|
| Moderate events, users, and images | AdministratorController |
| Review notification logs | NotificationLogDB |
| Enforce policy actions | PolicyController |

## AdministratorController
| Responsibilities | Collaborator(s) |
|---|---|
| Remove events and profiles | EventDB, ProfileDB |
| Remove images for policy violations | ImageStorageController |
| Audit and query notifications | NotificationLogDB |
| Present and enforce policy | PolicyController |

============================================================
SHARED / SYSTEM (used by multiple roles)
============================================================

## EventController
| Responsibilities | Collaborator(s) |
|---|---|
| Load and save event data | EventDB |
| Validate registration window | ScheduleController |
| Update poster references | ImageStorageController |
| Provide event data to UIs | EventListActivity, EventActivity, OrganizerActivity |

## EventDB
| Responsibilities | Collaborator(s) |
|---|---|
| CRUD for events in Firestore | EventDBConnector |
| Query events by filters or sorts | FilterController |
| Provide snapshots/streams | EventController |
| Remove events on admin action | AdministratorController |

## EventDBConnector
| Responsibilities | Collaborator(s) |
|---|---|
| Connect to Firebase Firestore (events) | EventDB |
| Handle async reads and writes | EventController |
| Map documents to domain objects | EventDB |

## WaitingListActivity
| Responsibilities | Collaborator(s) |
|---|---|
| Show waiting list size and entrants | WaitingListController |
| Allow join or leave actions | EntrantController |
| Show location map of joins | GeolocationService |

## WaitingListController
| Responsibilities | Collaborator(s) |
|---|---|
| Add or remove entrants from list | EntrantController |
| Enforce optional capacity limits | OrganizerController |
| Provide pool to selection rounds | LotteryService, SelectionRoundController |
| Persist list updates | WaitingListDB |
| Provide size and membership to UIs | EventActivity, WaitingListActivity |

## WaitingListDB
| Responsibilities | Collaborator(s) |
|---|---|
| CRUD for waiting list entries | WaitingListController |
| Store geolocation snapshots | GeolocationService |
| Stream list changes to UI | WaitingListController |

## SelectionRoundController
| Responsibilities | Collaborator(s) |
|---|---|
| Represent one selection round | LotteryService |
| Track selected and replacement entrants | WaitingListController |
| Persist round outcomes | RegistrationDB |
| Notify winners and declines | NotificationController, InviteController |

## InviteController
| Responsibilities | Collaborator(s) |
|---|---|
| Create and update invites | InviteDB |
| Validate expiry and state transitions | SelectionRoundController |
| Notify entrants of changes | NotificationController |
| Reflect status in registration | RegistrationController |

## InviteDB
| Responsibilities | Collaborator(s) |
|---|---|
| CRUD for invites | InviteController |
| Index by event and entrant | RegistrationController |
| Purge expired invites | AdministratorController |

## RegistrationDB
| Responsibilities | Collaborator(s) |
|---|---|
| Store final enrollment per event | RegistrationController |
| Track cancellations and replacements | SelectionRoundController |
| Provide queries for exports | CSVExportController |

## LotteryService
| Responsibilities | Collaborator(s) |
|---|---|
| Randomly sample entrants for requested count | WaitingListController, SelectionRoundController |
| Log selection outcomes | NotificationLogDB |
| Trigger winner notifications | NotificationController |

## NotificationController
| Responsibilities | Collaborator(s) |
|---|---|
| Send winner and not-chosen notifications | EntrantController, OrganizerController |
| Respect opt-out preferences | ProfileDB |
| Record delivery metadata | NotificationLogDB |

## NotificationLogDB
| Responsibilities | Collaborator(s) |
|---|---|
| Store notification audit logs | NotificationController |
| Provide logs for admin review | AdministratorController |

## QRCodeActivity
| Responsibilities | Collaborator(s) |
|---|---|
| Display event QR for sharing | QRCodeService, EventController |
| Scan a QR and return result | QRCodeService, MainActivity |
| Route to event details or join flow | EventActivity, WaitingListActivity |

## QRCodeService
| Responsibilities | Collaborator(s) |
|---|---|
| Generate QR images for event data | EventController, ImageStorageController |
| Decode scanned payload to domain | EventController |
| Validate and sanitize payload | PolicyController |

## GeolocationService
| Responsibilities | Collaborator(s) |
|---|---|
| Capture entrant join locations (runtime permission) | EntrantController, WaitingListController |
| Verify organizer geofence settings | OrganizerController, EventController |
| Provide distance and mapping utilities | EventListActivity, EventActivity |
| Render simple map bitmaps if needed | EventActivity |

## FilterController
| Responsibilities | Collaborator(s) |
|---|---|
| Hold user filter preferences (category, time, distance) | EventListActivity |
| Build query constraints for DB | EventDB, GeolocationService |
| Reset or clear filters | EventListActivity |

## ScheduleController
| Responsibilities | Collaborator(s) |
|---|---|
| Evaluate if registration is open or closed | EventController |
| Compute countdown or remaining time | EventActivity |
| Validate schedule edits | OrganizerController |

## ImageStorageController
| Responsibilities | Collaborator(s) |
|---|---|
| Upload, update, delete posters | OrganizerController, AdministratorController |
| Return poster URLs to event pages | EventController |
| Moderate removals on policy actions | AdministratorController, PolicyController |

## CSVExportController
| Responsibilities | Collaborator(s) |
|---|---|
| Export final enrolled list to CSV | RegistrationController |
| Provide file handle or URI to UI | RegistrationActivity |

## ProfileDB
| Responsibilities | Collaborator(s) |
|---|---|
| CRUD for entrant profiles | EntrantController |
| Sync profile changes to UI | EntrantActivity |
| Store notification preferences | NotificationController |

## AuthenticationService
| Responsibilities | Collaborator(s) |
|---|---|
| Provide unique device identifier | EntrantController |
| Bind and unbind device to account | EntrantController, AdministratorController |
| Support secure rebind on logout | EntrantActivity |

## PolicyController
| Responsibilities | Collaborator(s) |
|---|---|
| Store and present policy text | AdministratorActivity |
| Flag suspected policy violations | AdministratorController |
| Validate inbound data (basic checks) | QRCodeService, ImageStorageController |
